import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Metier } from 'app/shared/model/metier.model';
import { MetierService } from './metier.service';
import { MetierComponent } from './metier.component';
import { MetierDetailComponent } from './metier-detail.component';
import { MetierUpdateComponent } from './metier-update.component';
import { MetierDeletePopupComponent } from './metier-delete-dialog.component';
import { IMetier } from 'app/shared/model/metier.model';

@Injectable({ providedIn: 'root' })
export class MetierResolve implements Resolve<IMetier> {
    constructor(private service: MetierService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMetier> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Metier>) => response.ok),
                map((metier: HttpResponse<Metier>) => metier.body)
            );
        }
        return of(new Metier());
    }
}

export const metierRoute: Routes = [
    {
        path: '',
        component: MetierComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.metier.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MetierDetailComponent,
        resolve: {
            metier: MetierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.metier.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MetierUpdateComponent,
        resolve: {
            metier: MetierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.metier.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MetierUpdateComponent,
        resolve: {
            metier: MetierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.metier.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const metierPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MetierDeletePopupComponent,
        resolve: {
            metier: MetierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.metier.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
