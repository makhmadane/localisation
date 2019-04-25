import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeTransport } from 'app/shared/model/type-transport.model';
import { TypeTransportService } from './type-transport.service';
import { TypeTransportComponent } from './type-transport.component';
import { TypeTransportDetailComponent } from './type-transport-detail.component';
import { TypeTransportUpdateComponent } from './type-transport-update.component';
import { TypeTransportDeletePopupComponent } from './type-transport-delete-dialog.component';
import { ITypeTransport } from 'app/shared/model/type-transport.model';

@Injectable({ providedIn: 'root' })
export class TypeTransportResolve implements Resolve<ITypeTransport> {
    constructor(private service: TypeTransportService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypeTransport> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeTransport>) => response.ok),
                map((typeTransport: HttpResponse<TypeTransport>) => typeTransport.body)
            );
        }
        return of(new TypeTransport());
    }
}

export const typeTransportRoute: Routes = [
    {
        path: '',
        component: TypeTransportComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.typeTransport.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TypeTransportDetailComponent,
        resolve: {
            typeTransport: TypeTransportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.typeTransport.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TypeTransportUpdateComponent,
        resolve: {
            typeTransport: TypeTransportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.typeTransport.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TypeTransportUpdateComponent,
        resolve: {
            typeTransport: TypeTransportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.typeTransport.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeTransportPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TypeTransportDeletePopupComponent,
        resolve: {
            typeTransport: TypeTransportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.typeTransport.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
