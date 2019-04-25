import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { BonLivraison } from 'app/shared/model/bon-livraison.model';
import { BonLivraisonService } from './bon-livraison.service';
import { BonLivraisonComponent } from './bon-livraison.component';
import { BonLivraisonDetailComponent } from './bon-livraison-detail.component';
import { BonLivraisonUpdateComponent } from './bon-livraison-update.component';
import { BonLivraisonDeletePopupComponent } from './bon-livraison-delete-dialog.component';
import { IBonLivraison } from 'app/shared/model/bon-livraison.model';

@Injectable({ providedIn: 'root' })
export class BonLivraisonResolve implements Resolve<IBonLivraison> {
    constructor(private service: BonLivraisonService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBonLivraison> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<BonLivraison>) => response.ok),
                map((bonLivraison: HttpResponse<BonLivraison>) => bonLivraison.body)
            );
        }
        return of(new BonLivraison());
    }
}

export const bonLivraisonRoute: Routes = [
    {
        path: '',
        component: BonLivraisonComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.bonLivraison.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BonLivraisonDetailComponent,
        resolve: {
            bonLivraison: BonLivraisonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.bonLivraison.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BonLivraisonUpdateComponent,
        resolve: {
            bonLivraison: BonLivraisonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.bonLivraison.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BonLivraisonUpdateComponent,
        resolve: {
            bonLivraison: BonLivraisonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.bonLivraison.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bonLivraisonPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BonLivraisonDeletePopupComponent,
        resolve: {
            bonLivraison: BonLivraisonResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.bonLivraison.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
