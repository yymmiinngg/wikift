import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from './layout.component';

const LAYOUT_ROUTES: Routes = [
    { path: '', component: LayoutComponent, children: [
        { path: '', redirectTo: 'home', pathMatch: 'full' },

    ]}
];

export const LayoutRouting = RouterModule.forChild(LAYOUT_ROUTES);
