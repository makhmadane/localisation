import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Appro } from 'app/shared/model/appro.model';
import { ApproService } from './appro.service';
import { ApproComponent } from './appro.component';
import { ApproDetailComponent } from './appro-detail.component';
import { ApproUpdateComponent } from './appro-update.component';
import { ApproDeletePopupComponent } from './appro-delete-dialog.component';
import { IAppro } from 'app/shared/model/appro.model';

@Injectable({ providedIn: 'root' })
export class ApproResolve implements Resolve<IAppro> {
    constructor(private service: ApproService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IAppro> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Appro>) => response.ok),
                map((appro: HttpResponse<Appro>) => appro.body)
            );
        }
        return of(new Appro());
    }
}

export const approRoute: Routes = [
    {
        path: '',
        component: ApproComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.appro.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ApproDetailComponent,
        resolve: {
            appro: ApproResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.appro.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ApproUpdateComponent,
        resolve: {
            appro: ApproResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.appro.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ApproUpdateComponent,
        resolve: {
            appro: ApproResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.appro.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const approPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ApproDeletePopupComponent,
        resolve: {
            appro: ApproResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.appro.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
