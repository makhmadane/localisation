package org.yescola.livraison.config;

import java.time.Duration;

import org.ehcache.config.builders.*;
import org.ehcache.jsr107.Eh107Configuration;

import io.github.jhipster.config.jcache.BeanClassLoaderAwareJCacheRegionFactory;
import io.github.jhipster.config.JHipsterProperties;

import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        BeanClassLoaderAwareJCacheRegionFactory.setBeanClassLoader(this.getClass().getClassLoader());
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(ehcache.getTimeToLiveSeconds())))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(org.yescola.livraison.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(org.yescola.livraison.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Prospection.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Prospection.class.getName() + ".boutiques", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Metier.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Metier.class.getName() + ".boutiques", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Boutique.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Boutique.class.getName() + ".stockInitials", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Boutique.class.getName() + ".detailRoutes", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Boutique.class.getName() + ".routes", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Qualite.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Qualite.class.getName() + ".boutiques", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Secteur.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Secteur.class.getName() + ".routes", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Secteur.class.getName() + ".communes", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Secteur.class.getName() + ".boutiques", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Secteur.class.getName() + ".commandes", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Commune.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Commune.class.getName() + ".secteurs", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Commande.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Commande.class.getName() + ".detailComs", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Commande.class.getName() + ".prospections", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Commande.class.getName() + ".reglements", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.DetailCom.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.StockInitial.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Article.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Article.class.getName() + ".stockInitials", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Article.class.getName() + ".detailComs", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Parfum.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Parfum.class.getName() + ".articles", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Tablette.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Depot.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Depot.class.getName() + ".articles", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Route.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Route.class.getName() + ".detailRoutes", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Route.class.getName() + ".secteurs", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Route.class.getName() + ".boutiques", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.TypeRoute.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.TypeRoute.class.getName() + ".routes", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.DetailRoute.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Reglement.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.TypeRglment.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.TypeRglment.class.getName() + ".reglements", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.MoyenTransport.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.MoyenTransport.class.getName() + ".routes", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.MoyenTransport.class.getName() + ".prospections", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.TypeTransport.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.TypeTransport.class.getName() + ".moyenTransports", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Prospection.class.getName() + ".commandes", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Employee.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Article.class.getName() + ".appros", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.BonLivraison.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.BonLivraison.class.getName() + ".appros", jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Appro.class.getName(), jcacheConfiguration);
            cm.createCache(org.yescola.livraison.domain.Boutique_route.class.getName(),jcacheConfiguration);

            // jhipster-needle-ehcache-add-entry
        };
    }
}
