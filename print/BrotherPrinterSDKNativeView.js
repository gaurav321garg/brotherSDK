import React from 'react';
import { requireNativeComponent } from 'react-native';

const BrotherPrinterSDK = requireNativeComponent('BrotherPrinterSDK', BrotherPrinterSDKView);

export default class BrotherPrinterSDKView extends React.Component {
    render() {
        return <BrotherPrinterSDK {...this.props} />
    }
}

BrotherPrinterSDKView.propTypes = {
};
