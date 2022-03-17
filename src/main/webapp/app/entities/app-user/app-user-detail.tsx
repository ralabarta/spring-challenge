import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './app-user.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AppUserDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const appUserEntity = useAppSelector(state => state.appUser.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="appUserDetailsHeading">
          <Translate contentKey="mytestappApp.appUser.detail.title">AppUser</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.id}</dd>
          <dt>
            <span id="username">
              <Translate contentKey="mytestappApp.appUser.username">Username</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.username}</dd>
          <dt>
            <span id="password">
              <Translate contentKey="mytestappApp.appUser.password">Password</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.password}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="mytestappApp.appUser.firstName">First Name</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="mytestappApp.appUser.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.lastName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="mytestappApp.appUser.email">Email</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.email}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="mytestappApp.appUser.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{appUserEntity.phoneNumber}</dd>
          <dt>
            <span id="lastLogindate">
              <Translate contentKey="mytestappApp.appUser.lastLogindate">Last Logindate</Translate>
            </span>
          </dt>
          <dd>
            {appUserEntity.lastLogindate ? (
              <TextFormat value={appUserEntity.lastLogindate} type="date" format={APP_LOCAL_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <Translate contentKey="mytestappApp.appUser.rol">Rol</Translate>
          </dt>
          <dd>{appUserEntity.rol ? appUserEntity.rol.roleName : ''}</dd>
        </dl>
        <Button tag={Link} to="/app-user" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/app-user/${appUserEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AppUserDetail;
