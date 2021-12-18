import { MapdataService } from './../mapdata.service';
import { Component, OnInit } from '@angular/core';
import { environment } from 'src/environments/environment';
import * as mapboxgl from 'mapbox-gl';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css']
})
export class MapComponent implements OnInit {
  map!: mapboxgl.Map;
  style = 'mapbox://styles/emprahh/ckw15cxor5zgr14pc8f26y7bs';
  lat = 37.75;
  lng = -122.41;
  dataLoaded:boolean = false;

  constructor(private mapData:MapdataService) {
  }

  ngOnInit(): void {
    var mapboxgl = require('mapbox-gl/dist/mapbox-gl.js');
    mapboxgl.accessToken = environment.mapbox.accessToken;
    this.map = new mapboxgl.Map({
      container: "map",
      style: this.style,
      zoom: 2,
      center: [-20.5917, 40.6699]
  });    // Add map controls
    // this.map.addControl(new mapboxgl.NavigationControl());

     
    this.map.on('load', async () => {
      this.map.addSource('tweetdata', {
      type: 'geojson',
      data: await this.gendata(),
      cluster: true,
      clusterMaxZoom: 14, // Max zoom to cluster points on
      clusterRadius: 50, // Radius of each cluster when clustering points (defaults to 50)
      "clusterProperties": {
        "sum": ["+", ["get", "sentiment"]],
      }
      });

      this.map.addLayer({
      id: 'clusters',
      type: 'circle',
      source: 'tweetdata',
      filter: ['has', 'point_count'],
      paint: {
      'circle-color': [
        "interpolate",
        ["linear"],
        ["/", ["number", ["get", "sum"]], ["number", ["get", "point_count"]]],
        -1, '#f54242',
        -0.5, '#f542aa', 
        0,'#d442f5', 
        0.5, '#8742f5', 
        1, '#4842f5'
      ],
      'circle-radius': [
      'step',
      ['get', 'point_count'],
      20,
      100,
      30,
      750,
      40
      ]
      }
      });
      
      this.map.addLayer({
      id: 'cluster-count',
      type: 'symbol',
      source: 'tweetdata',
      filter: ['has', 'point_count'],
      layout: {
      'text-field': '{point_count_abbreviated}',
      'text-font': ['DIN Offc Pro Medium', 'Arial Unicode MS Bold'],
      'text-size': 12
      }
      });
      
      this.map.addLayer({
      id: 'unclustered-point',
      type: 'circle',
      source: 'tweetdata',
      filter: ['!', ['has', 'point_count']],
      paint: {
      'circle-color': '#11b4da',
      'circle-radius': 4,
      'circle-stroke-width': 1,
      'circle-stroke-color': '#fff'
      }
      });
    });
  };
  
  async gendata():Promise<any>{
    let dateNow = Date.today()
    dateNow.setTime(Date.now())
    let lastWeek = dateNow.addDays(-7)
    dateNow = Date.today()
    console.log(dateNow, lastWeek)
    const geojson = await this.mapData.getMapData(lastWeek.toString("yyyy-MM-dd HH:mm"), dateNow.toString("yyyy-MM-dd HH:mm"))
    return geojson
  }


}
