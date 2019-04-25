import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DetailRoute } from 'app/shared/model/detail-route.model';
import { DetailRouteService } from './detail-route.service';
import { DetailRouteComponent } from './detail-route.component';
import { DetailRouteDetailComponent } from './detail-route-detail.component';
import { DetailRouteUpdateComponent } from './detail-route-update.component';
import { DetailRouteDeletePopupComponent } from './detail-route-delete-dialog.component';
import { IDetailRoute } from 'app/shared/model/detail-route.model';

@Injectable({ providedIn: 'root' })
export class DetailRouteResolve implements Resolve<IDetailRoute> {
    constructor(private service: DetailRouteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDetailRoute> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DetailRoute>) => response.ok),
                map((detailRoute: HttpResponse<DetailRoute>) => detailRoute.body)
            );
        }
        return of(new DetailRoute());
    }
}

export const detailRouteRoute: Routes = [
    {
        path: '',
        component: DetailRouteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.detailRoute.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DetailRouteDetailComponent,
        resolve: {
            detailRoute: DetailRouteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.detailRoute.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DetailRouteUpdateComponent,
        resolve: {
            detailRoute: DetailRouteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.detailRoute.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DetailRouteUpdateComponent,
        resolve: {
            detailRoute: DetailRouteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.detailRoute.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const detailRoutePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DetailRouteDeletePopupComponent,
        resolve: {
            detailRoute: DetailRouteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.detailRoute.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
