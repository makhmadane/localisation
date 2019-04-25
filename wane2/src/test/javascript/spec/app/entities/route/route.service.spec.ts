/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { RouteService } from 'app/entities/route/route.service';
import { IRoute, Route } from 'app/shared/model/route.model';

describe('Service Tests', () => {
    describe('Route Service', () => {
        let injector: TestBed;
        let service: RouteService;
        let httpMock: HttpTestingController;
        let elemDefault: IRoute;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(RouteService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Route(
                0,
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                currentDate,
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                'AAAAAAA',
                currentDate,
                currentDate,
                'AAAAAAA'
            );
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        dateCreation: currentDate.format(DATE_FORMAT),
                        datedepCom: currentDate.format(DATE_FORMAT),
                        dateRCom: currentDate.format(DATE_FORMAT),
                        datedepLiv: currentDate.format(DATE_FORMAT),
                        dateRLiv: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a Route', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        dateCreation: currentDate.format(DATE_FORMAT),
                        datedepCom: currentDate.format(DATE_FORMAT),
                        dateRCom: currentDate.format(DATE_FORMAT),
                        datedepLiv: currentDate.format(DATE_FORMAT),
                        dateRLiv: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateCreation: currentDate,
                        datedepCom: currentDate,
                        dateRCom: currentDate,
                        datedepLiv: currentDate,
                        dateRLiv: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Route(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Route', async () => {
                const returnedFromService = Object.assign(
                    {
                        numero: 'BBBBBB',
                        journee: 'BBBBBB',
                        dateCreation: currentDate.format(DATE_FORMAT),
                        datedepCom: currentDate.format(DATE_FORMAT),
                        dateRCom: currentDate.format(DATE_FORMAT),
                        heureDepCom: 'BBBBBB',
                        heureFinCom: 'BBBBBB',
                        heureDepLiv: 'BBBBBB',
                        heureFinLiv: 'BBBBBB',
                        journeeLiv: 'BBBBBB',
                        datedepLiv: currentDate.format(DATE_FORMAT),
                        dateRLiv: currentDate.format(DATE_FORMAT),
                        etat: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        dateCreation: currentDate,
                        datedepCom: currentDate,
                        dateRCom: currentDate,
                        datedepLiv: currentDate,
                        dateRLiv: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of Route', async () => {
                const returnedFromService = Object.assign(
                    {
                        numero: 'BBBBBB',
                        journee: 'BBBBBB',
                        dateCreation: currentDate.format(DATE_FORMAT),
                        datedepCom: currentDate.format(DATE_FORMAT),
                        dateRCom: currentDate.format(DATE_FORMAT),
                        heureDepCom: 'BBBBBB',
                        heureFinCom: 'BBBBBB',
                        heureDepLiv: 'BBBBBB',
                        heureFinLiv: 'BBBBBB',
                        journeeLiv: 'BBBBBB',
                        datedepLiv: currentDate.format(DATE_FORMAT),
                        dateRLiv: currentDate.format(DATE_FORMAT),
                        etat: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        dateCreation: currentDate,
                        datedepCom: currentDate,
                        dateRCom: currentDate,
                        datedepLiv: currentDate,
                        dateRLiv: currentDate
                    },
                    returnedFromService
                );
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

            it('should delete a Route', async () => {
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
