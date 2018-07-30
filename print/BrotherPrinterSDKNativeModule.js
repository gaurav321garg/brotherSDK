import { NativeModules } from 'react-native'

const {BrotherPrinterSDK} = NativeModules;

export default {
    print(uri, ip_address) {

        return BrotherPrinterSDK.print(uri, ip_address)
    }
}
