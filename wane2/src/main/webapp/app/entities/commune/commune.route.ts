import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Commune } from 'app/shared/model/commune.model';
import { CommuneService } from './commune.service';
import { CommuneComponent } from './commune.component';
import { CommuneDetailComponent } from './commune-detail.component';
import { CommuneUpdateComponent } from './commune-update.component';
import { CommuneDeletePopupComponent } from './commune-delete-dialog.component';
import { ICommune } from 'app/shared/model/commune.model';

@Injectable({ providedIn: 'root' })
export class CommuneResolve implements Resolve<ICommune> {
    constructor(private service: CommuneService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommune> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Commune>) => response.ok),
                map((commune: HttpResponse<Commune>) => commune.body)
            );
        }
        return of(new Commune());
    }
}

export const communeRoute: Routes = [
    {
        path: '',
        component: CommuneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.commune.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommuneDetailComponent,
        resolve: {
            commune: CommuneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.commune.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommuneUpdateComponent,
        resolve: {
            commune: CommuneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.commune.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommuneUpdateComponent,
        resolve: {
            commune: CommuneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.commune.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const communePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CommuneDeletePopupComponent,
        resolve: {
            commune: CommuneResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.commune.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
