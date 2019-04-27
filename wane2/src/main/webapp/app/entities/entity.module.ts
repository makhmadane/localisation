import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        RouterModule.forChild([
            {
                path: 'prospection',
                loadChildren: './prospection/prospection.module#YesColaProspectionModule'
            },

            {
                path: 'metier',
                loadChildren: './metier/metier.module#YesColaMetierModule'
            },
            {
                path: 'boutique',
                loadChildren: './boutique/boutique.module#YesColaBoutiqueModule'
            },
            {
                path: 'qualite',
                loadChildren: './qualite/qualite.module#YesColaQualiteModule'
            },
            {
                path: 'secteur',
                loadChildren: './secteur/secteur.module#YesColaSecteurModule'
            },
            {
                path: 'commune',
                loadChildren: './commune/commune.module#YesColaCommuneModule'
            },
            {
                path: 'commande',
                loadChildren: './commande/commande.module#YesColaCommandeModule'
            },

            {
                path: 'detail-com',
                loadChildren: './detail-com/detail-com.module#YesColaDetailComModule'
            },
            {
                path: 'stock-initial',
                loadChildren: './stock-initial/stock-initial.module#YesColaStockInitialModule'
            },
            {
                path: 'article',
                loadChildren: './article/article.module#YesColaArticleModule'
            },
            {
                path: 'parfum',
                loadChildren: './parfum/parfum.module#YesColaParfumModule'
            },
            {
                path: 'tablette',
                loadChildren: './tablette/tablette.module#YesColaTabletteModule'
            },
            {
                path: 'depot',
                loadChildren: './depot/depot.module#YesColaDepotModule'
            },
            {
                path: 'route',
                loadChildren: './route/route.module#YesColaRouteModule'
            },
            {
                path: 'type-route',
                loadChildren: './type-route/type-route.module#YesColaTypeRouteModule'
            },
            {
                path: 'detail-route',
                loadChildren: './detail-route/detail-route.module#YesColaDetailRouteModule'
            },
            {
                path: 'reglement',
                loadChildren: './reglement/reglement.module#YesColaReglementModule'
            },
            {
                path: 'type-rglment',
                loadChildren: './type-rglment/type-rglment.module#YesColaTypeRglmentModule'
            },
            {
                path: 'moyen-transport',
                loadChildren: './moyen-transport/moyen-transport.module#YesColaMoyenTransportModule'
            },
            {
                path: 'type-transport',
                loadChildren: './type-transport/type-transport.module#YesColaTypeTransportModule'
            },
            {
                path: 'prospection',
                loadChildren: './prospection/prospection.module#YesColaProspectionModule'
            },
            {
                path: 'commande',
                loadChildren: './commande/commande.module#YesColaCommandeModule'
            },
            {
                path: 'route',
                loadChildren: './route/route.module#YesColaRouteModule'
            },
            {
                path: 'type-route',
                loadChildren: './type-route/type-route.module#YesColaTypeRouteModule'
            },
            {
                path: 'employee',
                loadChildren: './employee/employee.module#YesColaEmployeeModule'
            },
            {
                path: 'prospection',
                loadChildren: './prospection/prospection.module#YesColaProspectionModule'
            },
            {
                path: 'boutique',
                loadChildren: './boutique/boutique.module#YesColaBoutiqueModule'
            },
            {
                path: 'commande',
                loadChildren: './commande/commande.module#YesColaCommandeModule'
            },
            {
                path: 'tablette',
                loadChildren: './tablette/tablette.module#YesColaTabletteModule'
            },
            {
                path: 'route',
                loadChildren: './route/route.module#YesColaRouteModule'
            },
            {
                path: 'moyen-transport',
                loadChildren: './moyen-transport/moyen-transport.module#YesColaMoyenTransportModule'
            },
            {
                path: 'employee',
                loadChildren: './employee/employee.module#YesColaEmployeeModule'
            },
            {
                path: 'article',
                loadChildren: './article/article.module#YesColaArticleModule'
            },
            {
                path: 'tablette',
                loadChildren: './tablette/tablette.module#YesColaTabletteModule'
            },
            {
                path: 'bon-livraison',
                loadChildren: './bon-livraison/bon-livraison.module#YesColaBonLivraisonModule'
            },
            {
                path: 'appro',
                loadChildren: './appro/appro.module#YesColaApproModule'
            }
            /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
        ])
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaEntityModule {}
