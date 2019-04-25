import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TypeRglment } from 'app/shared/model/type-rglment.model';
import { TypeRglmentService } from './type-rglment.service';
import { TypeRglmentComponent } from './type-rglment.component';
import { TypeRglmentDetailComponent } from './type-rglment-detail.component';
import { TypeRglmentUpdateComponent } from './type-rglment-update.component';
import { TypeRglmentDeletePopupComponent } from './type-rglment-delete-dialog.component';
import { ITypeRglment } from 'app/shared/model/type-rglment.model';

@Injectable({ providedIn: 'root' })
export class TypeRglmentResolve implements Resolve<ITypeRglment> {
    constructor(private service: TypeRglmentService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITypeRglment> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<TypeRglment>) => response.ok),
                map((typeRglment: HttpResponse<TypeRglment>) => typeRglment.body)
            );
        }
        return of(new TypeRglment());
    }
}

export const typeRglmentRoute: Routes = [
    {
        path: '',
        component: TypeRglmentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.typeRglment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: TypeRglmentDetailComponent,
        resolve: {
            typeRglment: TypeRglmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.typeRglment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: TypeRglmentUpdateComponent,
        resolve: {
            typeRglment: TypeRglmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.typeRglment.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: TypeRglmentUpdateComponent,
        resolve: {
            typeRglment: TypeRglmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.typeRglment.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeRglmentPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: TypeRglmentDeletePopupComponent,
        resolve: {
            typeRglment: TypeRglmentResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.typeRglment.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
