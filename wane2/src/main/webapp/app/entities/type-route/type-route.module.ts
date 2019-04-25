import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    TypeRouteComponent,
    TypeRouteDetailComponent,
    TypeRouteUpdateComponent,
    TypeRouteDeletePopupComponent,
    TypeRouteDeleteDialogComponent,
    typeRouteRoute,
    typeRoutePopupRoute
} from './';

const ENTITY_STATES = [...typeRouteRoute, ...typeRoutePopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeRouteComponent,
        TypeRouteDetailComponent,
        TypeRouteUpdateComponent,
        TypeRouteDeleteDialogComponent,
        TypeRouteDeletePopupComponent
    ],
    entryComponents: [TypeRouteComponent, TypeRouteUpdateComponent, TypeRouteDeleteDialogComponent, TypeRouteDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaTypeRouteModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
