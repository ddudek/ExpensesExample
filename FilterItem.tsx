import React from 'react';
import {StyleSheet, View, Text, TouchableHighlight} from 'react-native';
import {Colors} from 'react-native/Libraries/NewAppScreen';

interface Props {
  label: String;
  selected: boolean;
  onPress: any;
}

interface State {}

export default class FilterItem extends React.PureComponent<Props, State> {
  constructor(props: Props) {
    super(props);
  }

  onPressed = () => {
    this.props.onPress();
  };

  render = () => {
    return (
      <TouchableHighlight
        style={styles.container}
        underlayColor={this.props.selected ? Colors.light : Colors.dark}
        onPress={() => this.onPressed()}>
        <View
          style={
            this.props.selected
              ? styles.backgroundSelected
              : styles.backgroundNormal
          }>
          <Text
            style={
              this.props.selected
                ? styles.filterButtonSelected
                : styles.filterButtonNormal
            }>
            {this.props.label}
          </Text>
        </View>
      </TouchableHighlight>
    );
  };
}

const styles = StyleSheet.create({
  container: {
    margin: 5,
    borderRadius: 5,
  },
  backgroundSelected: {
    borderRadius: 5,
    backgroundColor: Colors.primary,
  },
  backgroundNormal: {
    borderRadius: 5,
    backgroundColor: Colors.lighter,
  },
  filterButtonSelected: {
    paddingVertical: 5,
    paddingHorizontal: 10,
    borderStyle: 'solid',
    borderWidth: 1,
    borderColor: Colors.primary,
    borderRadius: 5,
    alignItems: 'center',
    justifyContent: 'center',
    color: '#fff',
    fontSize: 12,
    fontFamily: 'OpenSans',
  },
  filterButtonNormal: {
    paddingVertical: 5,
    paddingHorizontal: 10,
    borderStyle: 'solid',
    borderWidth: 1,
    borderColor: Colors.primary,
    borderRadius: 5,
    alignItems: 'center',
    justifyContent: 'center',
    color: Colors.dark,
    fontSize: 12,
    fontFamily: 'OpenSans',
  },
});
