import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DetailCom } from 'app/shared/model/detail-com.model';
import { DetailComService } from './detail-com.service';
import { DetailComComponent } from './detail-com.component';
import { DetailComDetailComponent } from './detail-com-detail.component';
import { DetailComUpdateComponent } from './detail-com-update.component';
import { DetailComDeletePopupComponent } from './detail-com-delete-dialog.component';
import { IDetailCom } from 'app/shared/model/detail-com.model';

@Injectable({ providedIn: 'root' })
export class DetailComResolve implements Resolve<IDetailCom> {
    constructor(private service: DetailComService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDetailCom> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<DetailCom>) => response.ok),
                map((detailCom: HttpResponse<DetailCom>) => detailCom.body)
            );
        }
        return of(new DetailCom());
    }
}

export const detailComRoute: Routes = [
    {
        path: '',
        component: DetailComComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.detailCom.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DetailComDetailComponent,
        resolve: {
            detailCom: DetailComResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.detailCom.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DetailComUpdateComponent,
        resolve: {
            detailCom: DetailComResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.detailCom.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DetailComUpdateComponent,
        resolve: {
            detailCom: DetailComResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.detailCom.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const detailComPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DetailComDeletePopupComponent,
        resolve: {
            detailCom: DetailComResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.detailCom.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
