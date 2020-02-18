import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { map, catchError, tap } from "rxjs/operators";
import { Observable, of } from "rxjs";
import { Server, Image, Link, Flavor, Network } from "./models/Server"


@Injectable({
  providedIn: 'root'
})

export class ComputeService {
  instance_url = "http://localhost:8008";
  compute_url = `${this.instance_url}/api/v1/instance`;

  httpOptions = {
      headers:
        new HttpHeaders({
          'Content-Type': 'application/json'
        })
  };

  constructor(private httpClient: HttpClient) {
  }


  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.log(`${operation} failed: ${error.message}`);
      try {
        return JSON.parse(error)
      } catch (error) {
        console.error("Error doesn't have JSON format");
        return of(result as T);
      }
   };
  }

  getServers(): Observable<Array<Server>> {
    const url = `${this.compute_url}/all_stub`;
    return this
      .httpClient
      .get<Array<Server>>(url)
      .pipe(
        map((res: any) => {
          return res;
        }),
        tap(_ => console.log('Fetching servers')),
        catchError(this.handleError<Array<Server>>('getServers', []))
      );
  }

}
