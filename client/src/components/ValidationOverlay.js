import React, { Component } from "react";
import Tooltip from 'react-bootstrap/Tooltip';
import OverlayTrigger from 'react-bootstrap/OverlayTrigger';

import InputGroup from 'react-bootstrap/InputGroup';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'; 
import { faTimesCircle } from '@fortawesome/free-solid-svg-icons';


export default class ValidationOverlay extends Component {
  constructor(props) {
    super(props);
  }

  render() {
    const renderTooltip = (props) => (
      // TODO: Need to pass the validation message through props
      <Tooltip id="button-tooltip" {...props}>
        {this.props.validationMessage}
      </Tooltip>
    );

    return (
      <OverlayTrigger
        placement="right"
        delay={{ show: 250, hide: 400 }}
        overlay={renderTooltip}
      >
        <InputGroup.Text>
          <FontAwesomeIcon icon={faTimesCircle} color="red" />
        </InputGroup.Text>
      </OverlayTrigger>
    );
  }
}
