import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Commande } from 'app/shared/model/commande.model';
import { CommandeService } from './commande.service';
import { CommandeComponent } from './commande.component';
import { CommandeDetailComponent } from './commande-detail.component';
import { CommandeUpdateComponent } from './commande-update.component';
import { CommandeDeletePopupComponent } from './commande-delete-dialog.component';
import { ICommande } from 'app/shared/model/commande.model';

@Injectable({ providedIn: 'root' })
export class CommandeResolve implements Resolve<ICommande> {
    constructor(private service: CommandeService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ICommande> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Commande>) => response.ok),
                map((commande: HttpResponse<Commande>) => commande.body)
            );
        }
        return of(new Commande());
    }
}

export const commandeRoute: Routes = [
    {
        path: '',
        component: CommandeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.commande.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: CommandeDetailComponent,
        resolve: {
            commande: CommandeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.commande.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: CommandeUpdateComponent,
        resolve: {
            commande: CommandeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.commande.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: CommandeUpdateComponent,
        resolve: {
            commande: CommandeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.commande.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const commandePopupRoute: Routes = [
    {
        path: ':id/delete',
        component: CommandeDeletePopupComponent,
        resolve: {
            commande: CommandeResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.commande.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
