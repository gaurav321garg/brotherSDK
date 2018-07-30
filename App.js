import React, {Component} from 'react';
import {StyleSheet,Image, Text, TouchableOpacity, View} from 'react-native';
import {RNCamera} from 'react-native-camera';
import RNPrint from './print';


type Props = {};
export default class App extends Component<Props> {
    state = {
        page: 1
    }

    render() {
        if (this.state.page === 1) {
            return (
                <View style={styles.container}>
                    <RNCamera
                        ref={ref => {
                            this.camera = ref;
                        }}
                        style={styles.preview}
                        type={RNCamera.Constants.Type.back}
                        flashMode={RNCamera.Constants.FlashMode.off}
                        permissionDialogTitle={'Permission to use camera'}
                        permissionDialogMessage={'We need your permission to use your camera phone'}
                    />
                    <View style={{flex: 0, flexDirection: 'row', justifyContent: 'center',}}>
                        <TouchableOpacity
                            onPress={this.takePicture.bind(this)}
                            style={styles.capture}
                        >
                            <Text style={{fontSize: 14}}> SNAP </Text>
                        </TouchableOpacity>
                    </View>
                </View>
            );
        }
        else if (this.state.page === 2) {
            return (
                <View style={styles.container}>
                    <Image
                        source={{uri: this.state.data}}
                        style={styles.preview}
                    />
                    <View style={{flex: 0, flexDirection: 'row', justifyContent: 'center',}}>
                        <TouchableOpacity
                            onPress={()=>{
                                RNPrint.print(this.state.data, "192.168.1.61");

                                this.setState({page: 1})}}
                            style={styles.capture}
                        >
                            <Text style={{fontSize: 14}}> print </Text>
                        </TouchableOpacity>
                    </View>
                </View>
            );
        }


    }

    takePicture = async function () {
        if (this.camera) {
            const options = {quality: 0.5, base64: true,orientation:'portrait',fixOrientation:true};
            const data = await this.camera.takePictureAsync(options)
            console.warn(data.uri);
            this.setState({page: 2, data:data.uri})
        }
    };
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        flexDirection: 'column',
        backgroundColor: 'black'
    },
    preview: {
        flex: 1,
        justifyContent: 'flex-end',
        alignItems: 'center'
    },
    capture: {
        flex: 0,
        backgroundColor: '#fff',
        borderRadius: 5,
        padding: 15,
        paddingHorizontal: 20,
        alignSelf: 'center',
        margin: 20
    }
});