import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    DetailRouteComponent,
    DetailRouteDetailComponent,
    DetailRouteUpdateComponent,
    DetailRouteDeletePopupComponent,
    DetailRouteDeleteDialogComponent,
    detailRouteRoute,
    detailRoutePopupRoute
} from './';

const ENTITY_STATES = [...detailRouteRoute, ...detailRoutePopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DetailRouteComponent,
        DetailRouteDetailComponent,
        DetailRouteUpdateComponent,
        DetailRouteDeleteDialogComponent,
        DetailRouteDeletePopupComponent
    ],
    entryComponents: [DetailRouteComponent, DetailRouteUpdateComponent, DetailRouteDeleteDialogComponent, DetailRouteDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaDetailRouteModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
