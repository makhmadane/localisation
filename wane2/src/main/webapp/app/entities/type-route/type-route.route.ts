import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeRoute } from 'app/shared/model/type-route.model';
import { TypeRouteService } from './type-route.service';
import { TypeRouteComponent } from './type-route.component';
import { TypeRouteDetailComponent } from './type-route-detail.component';
import { TypeRouteUpdateComponent } from './type-route-update.component';
import { TypeRouteDeletePopupComponent } from './type-route-delete-dialog.component';
import { ITypeRoute } from 'app/shared/model/type-route.model';

@Injectable({ providedIn: 'root' })
export class TypeRouteResolve implements Resolve<ITypeRoute> {
    constructor(private service: TypeRouteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypeRoute> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeRoute>) => response.ok),
                map((typeRoute: HttpResponse<TypeRoute>) => typeRoute.body)
            );
        }
        return of(new TypeRoute());
    }
}

export const typeRouteRoute: Routes = [
    {
        path: '',
        component: TypeRouteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.typeRoute.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TypeRouteDetailComponent,
        resolve: {
            typeRoute: TypeRouteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.typeRoute.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TypeRouteUpdateComponent,
        resolve: {
            typeRoute: TypeRouteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.typeRoute.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TypeRouteUpdateComponent,
        resolve: {
            typeRoute: TypeRouteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.typeRoute.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeRoutePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TypeRouteDeletePopupComponent,
        resolve: {
            typeRoute: TypeRouteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.typeRoute.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
