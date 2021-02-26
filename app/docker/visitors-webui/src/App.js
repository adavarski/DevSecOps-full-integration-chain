import React, { Component } from 'react';

import VisitorsTable from './VisitorsTable.js';

class App extends Component {
  render() {

    let title = process.env.REACT_APP_TITLE || 'Visitors Dashboard'

    return (
      <div className="container">
        <div className="page-header">
          <h2>{title}</h2>
        </div>
        <div>
          <VisitorsTable/>
        </div>
      </div>
    );
  }
}

export default App;
