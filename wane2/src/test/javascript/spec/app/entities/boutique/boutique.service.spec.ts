/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import { BoutiqueService } from 'app/entities/boutique/boutique.service';
import { IBoutique, Boutique, Civilite } from 'app/shared/model/boutique.model';

describe('Service Tests', () => {
    describe('Boutique Service', () => {
        let injector: TestBed;
        let service: BoutiqueService;
        let httpMock: HttpTestingController;
        let elemDefault: IBoutique;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(BoutiqueService);
            httpMock = injector.get(HttpTestingController);

            elemDefault = new Boutique(
                0,
                'AAAAAAA',
                'AAAAAAA',
                Civilite.M,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign({}, elemDefault);
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Boutique', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .create(new Boutique(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Boutique', async () => {
                const returnedFromService = Object.assign(
                    {
                        nomBoutique: 'BBBBBB',
                        prenom: 'BBBBBB',
                        civilite: 'BBBBBB',
                        nom: 'BBBBBB',
                        telephone: 'BBBBBB',
                        telproprietaire: 'BBBBBB',
                        longitude: 'BBBBBB',
                        altitude: 'BBBBBB',
                        etat: 'BBBBBB',
                        periodicite: 'BBBBBB',
                        dateDernierCom: 'BBBBBB',
                        dateDernierLiv: 'BBBBBB',
                        solde: 'BBBBBB',
                        commandeEnCours: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign({}, returnedFromService);
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Boutique', async () => {
                const returnedFromService = Object.assign(
                    {
                        nomBoutique: 'BBBBBB',
                        prenom: 'BBBBBB',
                        civilite: 'BBBBBB',
                        nom: 'BBBBBB',
                        telephone: 'BBBBBB',
                        telproprietaire: 'BBBBBB',
                        longitude: 'BBBBBB',
                        altitude: 'BBBBBB',
                        etat: 'BBBBBB',
                        periodicite: 'BBBBBB',
                        dateDernierCom: 'BBBBBB',
                        dateDernierLiv: 'BBBBBB',
                        solde: 'BBBBBB',
                        commandeEnCours: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign({}, returnedFromService);
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Boutique', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
