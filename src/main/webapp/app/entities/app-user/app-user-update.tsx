import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IAppRole } from 'app/shared/model/app-role.model';
import { getEntities as getAppRoles } from 'app/entities/app-role/app-role.reducer';
import { getEntity, updateEntity, createEntity, reset } from './app-user.reducer';
import { IAppUser } from 'app/shared/model/app-user.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const AppUserUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const appRoles = useAppSelector(state => state.appRole.entities);
  const appUserEntity = useAppSelector(state => state.appUser.entity);
  const loading = useAppSelector(state => state.appUser.loading);
  const updating = useAppSelector(state => state.appUser.updating);
  const updateSuccess = useAppSelector(state => state.appUser.updateSuccess);
  const handleClose = () => {
    props.history.push('/app-user');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getAppRoles({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...appUserEntity,
      ...values,
      rol: appRoles.find(it => it.id.toString() === values.rol.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...appUserEntity,
          rol: appUserEntity?.rol?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="mytestappApp.appUser.home.createOrEditLabel" data-cy="AppUserCreateUpdateHeading">
            <Translate contentKey="mytestappApp.appUser.home.createOrEditLabel">Create or edit a AppUser</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="app-user-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('mytestappApp.appUser.username')}
                id="app-user-username"
                name="username"
                data-cy="username"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('mytestappApp.appUser.password')}
                id="app-user-password"
                name="password"
                data-cy="password"
                type="text"
              />
              <ValidatedField
                label={translate('mytestappApp.appUser.firstName')}
                id="app-user-firstName"
                name="firstName"
                data-cy="firstName"
                type="text"
              />
              <ValidatedField
                label={translate('mytestappApp.appUser.lastName')}
                id="app-user-lastName"
                name="lastName"
                data-cy="lastName"
                type="text"
              />
              <ValidatedField
                label={translate('mytestappApp.appUser.email')}
                id="app-user-email"
                name="email"
                data-cy="email"
                type="text"
              />
              <ValidatedField
                label={translate('mytestappApp.appUser.phoneNumber')}
                id="app-user-phoneNumber"
                name="phoneNumber"
                data-cy="phoneNumber"
                type="text"
              />
              <ValidatedField
                label={translate('mytestappApp.appUser.lastLogindate')}
                id="app-user-lastLogindate"
                name="lastLogindate"
                data-cy="lastLogindate"
                type="date"
              />
              <ValidatedField
                id="app-user-rol"
                name="rol"
                data-cy="rol"
                label={translate('mytestappApp.appUser.rol')}
                type="select"
                required
              >
                <option value="" key="0" />
                {appRoles
                  ? appRoles.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.roleName}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <FormText>
                <Translate contentKey="entity.validation.required">This field is required.</Translate>
              </FormText>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/app-user" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default AppUserUpdate;
