import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Boutique } from 'app/shared/model/boutique.model';
import { BoutiqueService } from './boutique.service';
import { BoutiqueComponent } from './boutique.component';
import { BoutiqueDetailComponent } from './boutique-detail.component';
import { BoutiqueUpdateComponent } from './boutique-update.component';
import { BoutiqueDeletePopupComponent } from './boutique-delete-dialog.component';
import { IBoutique } from 'app/shared/model/boutique.model';

@Injectable({ providedIn: 'root' })
export class BoutiqueResolve implements Resolve<IBoutique> {
    constructor(private service: BoutiqueService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IBoutique> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Boutique>) => response.ok),
                map((boutique: HttpResponse<Boutique>) => boutique.body)
            );
        }
        return of(new Boutique());
    }
}

export const boutiqueRoute: Routes = [
    {
        path: '',
        component: BoutiqueComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.boutique.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: BoutiqueDetailComponent,
        resolve: {
            boutique: BoutiqueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.boutique.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: BoutiqueUpdateComponent,
        resolve: {
            boutique: BoutiqueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.boutique.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: BoutiqueUpdateComponent,
        resolve: {
            boutique: BoutiqueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.boutique.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const boutiquePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: BoutiqueDeletePopupComponent,
        resolve: {
            boutique: BoutiqueResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.boutique.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
