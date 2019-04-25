import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Prospection } from 'app/shared/model/prospection.model';
import { ProspectionService } from './prospection.service';
import { ProspectionComponent } from './prospection.component';
import { ProspectionDetailComponent } from './prospection-detail.component';
import { ProspectionUpdateComponent } from './prospection-update.component';
import { ProspectionDeletePopupComponent } from './prospection-delete-dialog.component';
import { IProspection } from 'app/shared/model/prospection.model';

@Injectable({ providedIn: 'root' })
export class ProspectionResolve implements Resolve<IProspection> {
    constructor(private service: ProspectionService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IProspection> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Prospection>) => response.ok),
                map((prospection: HttpResponse<Prospection>) => prospection.body)
            );
        }
        return of(new Prospection());
    }
}

export const prospectionRoute: Routes = [
    {
        path: '',
        component: ProspectionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.prospection.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ProspectionDetailComponent,
        resolve: {
            prospection: ProspectionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.prospection.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ProspectionUpdateComponent,
        resolve: {
            prospection: ProspectionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.prospection.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ProspectionUpdateComponent,
        resolve: {
            prospection: ProspectionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.prospection.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const prospectionPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ProspectionDeletePopupComponent,
        resolve: {
            prospection: ProspectionResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.prospection.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
