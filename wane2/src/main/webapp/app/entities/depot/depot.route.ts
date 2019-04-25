import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Depot } from 'app/shared/model/depot.model';
import { DepotService } from './depot.service';
import { DepotComponent } from './depot.component';
import { DepotDetailComponent } from './depot-detail.component';
import { DepotUpdateComponent } from './depot-update.component';
import { DepotDeletePopupComponent } from './depot-delete-dialog.component';
import { IDepot } from 'app/shared/model/depot.model';

@Injectable({ providedIn: 'root' })
export class DepotResolve implements Resolve<IDepot> {
    constructor(private service: DepotService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDepot> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Depot>) => response.ok),
                map((depot: HttpResponse<Depot>) => depot.body)
            );
        }
        return of(new Depot());
    }
}

export const depotRoute: Routes = [
    {
        path: '',
        component: DepotComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.depot.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: DepotDetailComponent,
        resolve: {
            depot: DepotResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.depot.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: DepotUpdateComponent,
        resolve: {
            depot: DepotResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.depot.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: DepotUpdateComponent,
        resolve: {
            depot: DepotResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.depot.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const depotPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: DepotDeletePopupComponent,
        resolve: {
            depot: DepotResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.depot.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
