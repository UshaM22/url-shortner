package com.urlshortener.service;

import com.urlshortener.model.UrlDetail;
import com.urlshortener.repository.UrlDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class UrlService {

    @Autowired
    private UrlDetailRepository urlDetailRepository;

    @Value("${app.base-url}")
    private String baseUrl;

    @Value("${app.default-expiry-days}")
    private int expiryDays;

    public String createShortCode(String longUrl) {
        int leftLimit = 48; // numeral 0
        int rightLimit = 122; // letter z
        int targetStringLength = 6;
        String generateShortCode;
        boolean codeExists;
        Random random = new Random();

        do {
            generateShortCode = random.ints(leftLimit, rightLimit + 1)
                    .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                    .limit(targetStringLength)
                    .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                    .toString();
            codeExists = urlDetailRepository.existsByShortCode(generateShortCode);

        } while (codeExists);

        UrlDetail urlDetail = new UrlDetail();
        String shortCode = generateShortCode;
        urlDetail.setShortCode(shortCode);
        LocalDateTime createdAt = LocalDateTime.now();
        urlDetail.setCreatedAt(createdAt);
        urlDetail.setExpiresAt(createdAt.plusDays(expiryDays));
        urlDetail.setLongUrl(longUrl);
        UrlDetail saved = urlDetailRepository.save(urlDetail);

        return baseUrl + "/" + shortCode;


    }
}
