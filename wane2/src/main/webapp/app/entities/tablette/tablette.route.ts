import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Tablette } from 'app/shared/model/tablette.model';
import { TabletteService } from './tablette.service';
import { TabletteComponent } from './tablette.component';
import { TabletteDetailComponent } from './tablette-detail.component';
import { TabletteUpdateComponent } from './tablette-update.component';
import { TabletteDeletePopupComponent } from './tablette-delete-dialog.component';
import { ITablette } from 'app/shared/model/tablette.model';

@Injectable({ providedIn: 'root' })
export class TabletteResolve implements Resolve<ITablette> {
    constructor(private service: TabletteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITablette> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Tablette>) => response.ok),
                map((tablette: HttpResponse<Tablette>) => tablette.body)
            );
        }
        return of(new Tablette());
    }
}

export const tabletteRoute: Routes = [
    {
        path: '',
        component: TabletteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.tablette.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TabletteDetailComponent,
        resolve: {
            tablette: TabletteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.tablette.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TabletteUpdateComponent,
        resolve: {
            tablette: TabletteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.tablette.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TabletteUpdateComponent,
        resolve: {
            tablette: TabletteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.tablette.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tablettePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TabletteDeletePopupComponent,
        resolve: {
            tablette: TabletteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.tablette.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
