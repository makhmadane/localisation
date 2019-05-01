import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';
import { DragDropModule } from '@angular/cdk/drag-drop';
import { YesColaSharedModule } from 'app/shared';
import {
    CommandeComponent,
    CommandeDetailComponent,
    CommandeUpdateComponent,
    CommandeDeletePopupComponent,
    CommandeDeleteDialogComponent,
    commandeRoute,
    commandePopupRoute
} from './';

const ENTITY_STATES = [...commandeRoute, ...commandePopupRoute];

@NgModule({
    imports: [YesColaSharedModule, RouterModule.forChild(ENTITY_STATES),DragDropModule],
    declarations: [
        CommandeComponent,
        CommandeDetailComponent,
        CommandeUpdateComponent,
        CommandeDeleteDialogComponent,
        CommandeDeletePopupComponent
    ],
    entryComponents: [CommandeComponent, CommandeUpdateComponent, CommandeDeleteDialogComponent, CommandeDeletePopupComponent],
    providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class Suivi_Com_Module {
    constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
        this.languageHelper.language.subscribe((languageKey: string) => {
            if (languageKey !== undefined) {
                this.languageService.changeLanguage(languageKey);
            }
        });
    }
}
