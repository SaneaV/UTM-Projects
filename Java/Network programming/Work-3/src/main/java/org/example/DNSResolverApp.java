package org.example;

import org.xbill.DNS.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.xbill.DNS.Record;

public class DNSResolverApp {
  private SimpleResolver resolver;

  public DNSResolverApp() {
    try {
      // Initialize with the default DNS server
      resolver = new SimpleResolver();
    } catch (UnknownHostException e) {
      System.err.println("Error initializing DNS resolver: " + e.getMessage());
    }
  }

  public void run() {
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    System.out.println("DNS Resolver Application is ready to use.");

    while (true) {
      System.out.print("Enter command (resolve <domain/IP> or use dns <ip>): ");
      try {
        String line = reader.readLine().trim();
        if (line.isEmpty()) continue;

        String[] command = line.split("\\s+");
        if (command.length == 0) continue;

        if (command[0].equalsIgnoreCase("resolve")) {
          if (command.length != 2) {
            System.out.println("Error: Invalid command. Use 'resolve <domain/IP>'.");
          } else {
            String target = command[1];
            if (target.matches("\\d{1,3}(\\.\\d{1,3}){3}")) {
              // It's an IP address
              resolveIP(target);
            } else {
              // It's a domain name
              resolveDomain(target);
            }
          }
        } else if (command[0].equalsIgnoreCase("use") && command.length == 3 && command[1].equalsIgnoreCase("dns")) {
          setDNSServer(command[2]);
        } else {
          System.out.println("Unknown command. Use 'resolve <domain/IP>' or 'use dns <ip>'.");
        }
      } catch (IOException e) {
        System.err.println("Error reading input: " + e.getMessage());
      }
    }
  }

  private void resolveDomain(String domain) {
    try {
      Name name = Name.fromString(domain, Name.root);
      Record question = Record.newRecord(name, Type.A, DClass.IN);
      Message query = Message.newQuery(question);
      Message response = resolver.send(query);
      Record[] answers = response.getSectionArray(Section.ANSWER);

      if (answers.length == 0) {
        System.out.println("No A records found for domain " + domain);
      } else {
        System.out.println("IP Addresses for " + domain + ":");
        for (Record record : answers) {
          ARecord aRecord = (ARecord) record;
          System.out.println(aRecord.getAddress().getHostAddress());
        }
      }
    } catch (IOException e) {
      System.err.println("Error resolving domain " + domain + ": " + e.getMessage());
    }
  }

  private void resolveIP(String ip) {
    try {
      Name name = ReverseMap.fromAddress(ip);
      Record question = Record.newRecord(name, Type.PTR, DClass.IN);
      Message query = Message.newQuery(question);
      Message response = resolver.send(query);
      Record[] answers = response.getSectionArray(Section.ANSWER);

      if (answers.length == 0) {
        System.out.println("No PTR records found for IP " + ip);
      } else {
        System.out.println("Hostnames for " + ip + ":");
        for (Record record : answers) {
          PTRRecord ptrRecord = (PTRRecord) record;
          System.out.println(ptrRecord.getTarget().toString(true));
        }
      }
    } catch (IOException e) {
      System.err.println("Error resolving IP " + ip + ": " + e.getMessage());
    }
  }

  private void setDNSServer(String dnsServer) {
    try {
      InetAddress inetAddress = InetAddress.getByName(dnsServer);
      resolver = new SimpleResolver(inetAddress.getHostAddress());
      System.out.println("DNS server set to " + dnsServer);
    } catch (UnknownHostException e) {
      System.err.println("Error: Invalid DNS server IP address '" + dnsServer + "'. " + e.getMessage());
    }
  }

  public static void main(String[] args) {
    DNSResolverApp app = new DNSResolverApp();
    app.run();
  }
}