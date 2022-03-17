import './footer.scss';

import React from 'react';
import { Translate } from 'react-jhipster';
import { Col, Row } from 'reactstrap';

const Footer = () => (
  <div className="footer page-content">
    <Row>
      <Col md="12">
        <p>
          <Translate contentKey="footer">App User Challenge - 2022</Translate>
        </p>
      </Col>
    </Row>
  </div>
);

export default Footer;
