package com.gfg.jbdl10urlshortener.resource;

import com.gfg.jbdl10urlshortener.models.URLShortenRequest;
import com.gfg.jbdl10urlshortener.service.URLShortenerService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@RestController
public class controller {
    @Autowired
    private URLShortenerService urlShortenerService;
    @CrossOrigin(origins= "https://frontend-urlshortner.herokuapp.com")
    @PostMapping("/longurl/")
    ResponseEntity shortenUrl(@RequestBody URLShortenRequest urlShortenRequest){
        System.out.println("reached");
        return ResponseEntity.ok(urlShortenerService.shorten(urlShortenRequest.getUrl()));
    }
    @CrossOrigin(origins= "https://frontend-urlshortner.herokuapp.com")
    @GetMapping("{shorturl}")
    ResponseEntity getLongURL(@PathVariable("shorturl") String shorturl){
        try {
//            return  "redirect:"+ urlShortenerService.get(shorturl);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(URI.create(urlShortenerService.get(shorturl)));
            return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);

        } catch (Exception e) {
//           System.out.println("Not found");
            return ResponseEntity.ok(e.getMessage());
        }
    }
    @CrossOrigin(origins= "https://frontend-urlshortner.herokuapp.com")
    @PutMapping("/shorturl/expiration")
    ResponseEntity expire(){
        urlShortenerService.expire();
        return ResponseEntity.ok().build();
    }

}
