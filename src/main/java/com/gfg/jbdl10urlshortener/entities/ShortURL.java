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

public class ShortURL {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long  id;
    @Column(nullable = false)
    @Builder.Default
    private   String  domain="localhost:8080";
    @Column(nullable = false)
    @Builder.Default
    private   String protocol="http";
    @Builder.Default
    private   Boolean expired=false;
    @Column(updatable = false)
    @Temporal(value=TemporalType.TIMESTAMP)
    private Date createdAt;
    @OneToOne(cascade = CascadeType.ALL)
    private LongURL longURL;

}
