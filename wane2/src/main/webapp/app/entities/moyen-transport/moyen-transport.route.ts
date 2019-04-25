import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { MoyenTransport } from 'app/shared/model/moyen-transport.model';
import { MoyenTransportService } from './moyen-transport.service';
import { MoyenTransportComponent } from './moyen-transport.component';
import { MoyenTransportDetailComponent } from './moyen-transport-detail.component';
import { MoyenTransportUpdateComponent } from './moyen-transport-update.component';
import { MoyenTransportDeletePopupComponent } from './moyen-transport-delete-dialog.component';
import { IMoyenTransport } from 'app/shared/model/moyen-transport.model';

@Injectable({ providedIn: 'root' })
export class MoyenTransportResolve implements Resolve<IMoyenTransport> {
    constructor(private service: MoyenTransportService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IMoyenTransport> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<MoyenTransport>) => response.ok),
                map((moyenTransport: HttpResponse<MoyenTransport>) => moyenTransport.body)
            );
        }
        return of(new MoyenTransport());
    }
}

export const moyenTransportRoute: Routes = [
    {
        path: '',
        component: MoyenTransportComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.moyenTransport.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: MoyenTransportDetailComponent,
        resolve: {
            moyenTransport: MoyenTransportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.moyenTransport.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: MoyenTransportUpdateComponent,
        resolve: {
            moyenTransport: MoyenTransportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.moyenTransport.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: MoyenTransportUpdateComponent,
        resolve: {
            moyenTransport: MoyenTransportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.moyenTransport.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const moyenTransportPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: MoyenTransportDeletePopupComponent,
        resolve: {
            moyenTransport: MoyenTransportResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.moyenTransport.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
