package com.gfg.jbdl10urlshortener.service;

import com.gfg.jbdl10urlshortener.Repository.LongUrlRepository;
import com.gfg.jbdl10urlshortener.Repository.ShortUrlRepository;
import com.gfg.jbdl10urlshortener.entities.LongURL;
import com.gfg.jbdl10urlshortener.entities.ShortURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

import java.util.Base64;
import java.util.Date;

@Service
public class URLShorterServiceImpl implements URLShortenerService {
    @Autowired
    private ShortUrlRepository shortUrlRepository;
    @Autowired
    private LongUrlRepository longUrlRepository;
    @Override
    public String shorten(String longUrlString) {
        Date obj = new Date();
        ShortURL shortURL = ShortURL.builder()
                .domain("backend-urlshortner.herokuapp.com")
                .protocol("https")
                .expired(false)
                .createdAt(obj)
                .build();
        LongURL longURL = LongURL.builder()
                .createdAt(obj)
                .url(longUrlString)
                .shortURL(shortURL).build();
        shortURL.setLongURL(longURL);
        longURL = longUrlRepository.save(longURL);
        //build tiny url
        URI uri = URI.create(shortURL.getProtocol()+"://"+shortURL.getDomain()+"/"+getShortenedPath(longURL.getShortURL().getId()));
        return uri.toString();
    }

    private String getShortenedPath(Long Id) {
        String path = Base64.getEncoder().encodeToString(String.valueOf(Id).getBytes());
        return path;
    }

    @Override
    public String get(String shortUrlRequest) throws Exception {
        Long id = 0l;
        try {
             id = decodePath(shortUrlRequest);
        }catch (Exception e){
            throw new Exception("invalid shortUrl");
        }
        ShortURL shortURL = shortUrlRepository.findByIdAndExpired(id,false)
                .orElseThrow(()->  new Exception("shortURL doesn't exist or expired"));
        return shortURL.getLongURL().getUrl();
    }

    @Override
    public void expire() {
        Iterable<ShortURL> shortURLList = shortUrlRepository.findAll();
        shortURLList.forEach(
                shortURL -> {
                    if(!shortURL.getExpired()){
                        if(shortURL.getCreatedAt().before(new Timestamp(System.currentTimeMillis()-10000))){
                            shortURL.setExpired(true);
                            shortUrlRepository.save(shortURL);
                        }
                    }
                });
    }

    private Long decodePath(String shortUrl) {
        byte[] bytes = Base64.getDecoder().decode(shortUrl);
        String str = new String(bytes, StandardCharsets.UTF_8);
        return Long.parseLong(str);
    }
}
