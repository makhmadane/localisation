import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { StockInitial } from 'app/shared/model/stock-initial.model';
import { StockInitialService } from './stock-initial.service';
import { StockInitialComponent } from './stock-initial.component';
import { StockInitialDetailComponent } from './stock-initial-detail.component';
import { StockInitialUpdateComponent } from './stock-initial-update.component';
import { StockInitialDeletePopupComponent } from './stock-initial-delete-dialog.component';
import { IStockInitial } from 'app/shared/model/stock-initial.model';

@Injectable({ providedIn: 'root' })
export class StockInitialResolve implements Resolve<IStockInitial> {
    constructor(private service: StockInitialService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IStockInitial> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<StockInitial>) => response.ok),
                map((stockInitial: HttpResponse<StockInitial>) => stockInitial.body)
            );
        }
        return of(new StockInitial());
    }
}

export const stockInitialRoute: Routes = [
    {
        path: '',
        component: StockInitialComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.stockInitial.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: StockInitialDetailComponent,
        resolve: {
            stockInitial: StockInitialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.stockInitial.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: StockInitialUpdateComponent,
        resolve: {
            stockInitial: StockInitialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.stockInitial.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: StockInitialUpdateComponent,
        resolve: {
            stockInitial: StockInitialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.stockInitial.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stockInitialPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: StockInitialDeletePopupComponent,
        resolve: {
            stockInitial: StockInitialResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.stockInitial.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
