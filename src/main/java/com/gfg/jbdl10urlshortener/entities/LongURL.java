package com.gfg.jbdl10urlshortener.entities;

import lombok.*;


import javax.persistence.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LongURL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    @Column(nullable = false)
    private String url;
    @Column(updatable = false)
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date createdAt;
    @OneToOne(cascade = CascadeType.ALL)
    private ShortURL shortURL;
}
