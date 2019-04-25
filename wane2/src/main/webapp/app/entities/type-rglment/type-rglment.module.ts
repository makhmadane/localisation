import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { YesColaSharedModule } from 'app/shared';
import {
    TypeRglmentComponent,
    TypeRglmentDetailComponent,
    TypeRglmentUpdateComponent,
    TypeRglmentDeletePopupComponent,
    TypeRglmentDeleteDialogComponent,
    typeRglmentRoute,
    typeRglmentPopupRoute
} from './';

const ENTITY_STATES = [...typeRglmentRoute, ...typeRglmentPopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        TypeRglmentComponent,
        TypeRglmentDetailComponent,
        TypeRglmentUpdateComponent,
        TypeRglmentDeleteDialogComponent,
        TypeRglmentDeletePopupComponent
    ],
    entryComponents: [TypeRglmentComponent, TypeRglmentUpdateComponent, TypeRglmentDeleteDialogComponent, TypeRglmentDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class YesColaTypeRglmentModule {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
