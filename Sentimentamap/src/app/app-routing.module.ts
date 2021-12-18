import { ScatterChartComponent } from './scatter-chart/scatter-chart.component';
import { MapComponent } from './map/map.component';
import { PieChartComponent } from './pie-chart/pie-chart.component';
import { LandingPageComponent } from './landing-page/landing-page.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BubblesComponent } from './bubbles/bubbles.component';

const routes: Routes = [
  {
    path: '',
    component: LandingPageComponent,
  },
  {
    path: 'bubble',
    component: LandingPageComponent,
    children: [
      { path: '', component: BubblesComponent }
    ]
  },
  {
    path: 'pie',
    component: LandingPageComponent,
    children: [
      { path: '', component: PieChartComponent }
    ]
  },
  {
    path: 'scatter',
    component: LandingPageComponent,
    children: [
      { path: '', component: ScatterChartComponent }
    ]
  },
  {
    path: 'map',
    component: LandingPageComponent,
    children: [
      { path: '', component: MapComponent }
    ]
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
