import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AppRole from './app-role';
import AppRoleDetail from './app-role-detail';
import AppRoleUpdate from './app-role-update';
import AppRoleDeleteDialog from './app-role-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AppRoleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AppRoleUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AppRoleDetail} />
      <ErrorBoundaryRoute path={match.url} component={AppRole} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AppRoleDeleteDialog} />
  </>
);

export default Routes;
