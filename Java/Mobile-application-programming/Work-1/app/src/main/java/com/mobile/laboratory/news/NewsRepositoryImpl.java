package com.mobile.laboratory.news;

import static java.util.Arrays.asList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NewsRepositoryImpl implements NewsRepository {
    private static final String TITLE_CLASS = "elementor-post__title";
    private static final String IMAGE_CLASS = "elementor-post__thumbnail";
    private static final String DATA_SRC_ATTR = "data-src";
    private static final String HREF_ATTR = "href";
    private static final String A_TAG = "a";
    private static final String IMG_TAG = "img";
    private static final String EMPTY = "";
    private final String siteLink;
    private final Map<String, String> descriptionCache = new ConcurrentHashMap<>();

    @Override
    public List<News> getLastNews() {
        return getListNews();
    }

    private Elements getTitle(Document document) {
        return document.getElementsByClass(TITLE_CLASS);
    }

    @Override
    public String getDescription() {
        return descriptionCache.computeIfAbsent(siteLink, this::fetchDescription);
    }

    private String fetchDescription(String url) {
        try {
            Document articleDocument = getHtml(url);
            Elements paragraphs = articleDocument.getElementsByTag("p");
            return paragraphs.isEmpty() ? null : paragraphs.first().text();
        } catch (IOException e) {
            throw new RuntimeException("Error fetching article description", e);
        }
    }

    private Elements getLink(Document document) {
        return getTitle(document).select(A_TAG);
    }

    private Elements getImage(Document document) {
        return document.getElementsByClass(IMAGE_CLASS).select(IMG_TAG);
    }

    private Document getHtml(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    private List<News> getListNews() {
        Document parsedSite = getParsedSite(siteLink);
        List<Elements> newsFromThreads = getNewsFromThreads(parsedSite);
        Elements titles = newsFromThreads.get(0);
        Elements links = newsFromThreads.get(1);
        Elements images = newsFromThreads.get(2);
        return populateListOfNews(titles, links, images);
    }

    private List<Elements> getNewsFromThreads(Document parsedSite) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Future<Elements> titleFuture = executorService.submit(() -> getTitle(parsedSite));
        Future<Elements> linkFuture = executorService.submit(() -> getLink(parsedSite));
        Future<Elements> imageFuture = executorService.submit(() -> getImage(parsedSite));
        try {
            List<Elements> result = asList(titleFuture.get(), linkFuture.get(), imageFuture.get());
            executorService.shutdown();
            return result;
        } catch (InterruptedException | ExecutionException e) {
            executorService.shutdownNow();
            throw new RuntimeException("Error fetching news data", e);
        }
    }

    private Document getParsedSite(String siteLink) {
        try {
            return getHtml(siteLink);
        } catch (IOException e) {
            throw new RuntimeException("Error parsing site", e);
        }
    }

    private List<News> populateListOfNews(Elements titles, Elements links, Elements images) {
        List<News> newsList = new ArrayList<>();
        int maxSize = getMaxSize(titles.size(), links.size(), images.size());
        for (int i = 0; i < maxSize; i++) {
            String title = i < titles.size() ? titles.get(i).text() : EMPTY;
            String imageUrl = i < images.size() ? images.get(i).attr(DATA_SRC_ATTR) : EMPTY;
            String linkUrl = i < links.size() ? links.get(i).attr(HREF_ATTR) : EMPTY;
            newsList.add(new News(title, null, imageUrl, linkUrl));
        }
        return newsList;
    }

    private int getMaxSize(int... sizes) {
        return Arrays.stream(sizes)
                .max()
                .getAsInt();
    }
}