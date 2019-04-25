import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Qualite } from 'app/shared/model/qualite.model';
import { QualiteService } from './qualite.service';
import { QualiteComponent } from './qualite.component';
import { QualiteDetailComponent } from './qualite-detail.component';
import { QualiteUpdateComponent } from './qualite-update.component';
import { QualiteDeletePopupComponent } from './qualite-delete-dialog.component';
import { IQualite } from 'app/shared/model/qualite.model';
import {DashboardComponent} from "../../dashboard/dashboard.component";

@Injectable({ providedIn: 'root' })
export class QualiteResolve implements Resolve<IQualite> {
    constructor(private service: QualiteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IQualite> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Qualite>) => response.ok),
                map((qualite: HttpResponse<Qualite>) => qualite.body)
            );
        }
        return of(new Qualite());
    }
}

export const qualiteRoute: Routes = [
    {
        path: '',
        component: QualiteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.qualite.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: QualiteDetailComponent,
        resolve: {
            qualite: QualiteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.qualite.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: QualiteUpdateComponent,
        resolve: {
            qualite: QualiteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.qualite.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: QualiteUpdateComponent,
        resolve: {
            qualite: QualiteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.qualite.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const qualitePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: QualiteDeletePopupComponent,
        resolve: {
            qualite: QualiteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.qualite.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
