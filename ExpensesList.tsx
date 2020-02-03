/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 */

import React from 'react';
import {
  SafeAreaView,
  StyleSheet,
  View,
  Text,
  FlatList,
  TouchableOpacity,
  NativeModules,
  NativeEventEmitter,
} from 'react-native';
import NativeCallbacksBridge from './NativeCallbacksBridge';
import {format, parseISO} from 'date-fns';
import {Colors} from 'react-native/Libraries/NewAppScreen';
import FilterItem from './FilterItem';

interface State {
  selectedFilter: number;
  expenses: any[];
  loading: Boolean;
  error: String | null;
}

interface Props {
  initialState: State;
}

export default class ExpensesList extends React.PureComponent<Props, State> {
  eventEmitter: NativeEventEmitter;

  constructor(props: Props) {
    super(props);
    this.state = props.initialState;
  }

  stateChanged = (item: State) => {
    this.setState(item);
  };

  componentDidMount = () => {
    const {EventManager} = NativeModules;
    this.eventEmitter = new NativeEventEmitter(EventManager);
    this.eventEmitter.addListener('stateChanged', this.stateChanged);
    NativeCallbacksBridge.onViewReady();
  };

  componentWillUnmount() {
    this.eventEmitter.removeListener('stateChanged', this.stateChanged);
  }

  onItemPressed = item => {
    NativeCallbacksBridge.onExpenseItemClicked(item);
  };

  onFilterPressed = (index: number) => {
    NativeCallbacksBridge.onFilterSelected(index);
    this.setState({selectedFilter: index});
  };

  renderItem = ({item}) => {
    return (
      <TouchableOpacity onPress={() => this.onItemPressed(item)}>
        <View style={styles.itemContainer}>
          <Text style={styles.itemName}>
            {item.amount.value} {item.amount.currency}
          </Text>
          <Text style={styles.itemEmail}>
            {format(parseISO(item.date), 'dd.MM.yyyy H:mma')}
          </Text>
          <Text style={styles.itemPrice}>
            {item.user.first} {item.user.last}
          </Text>
          <Text style={styles.itemDate}>{item.user.email}</Text>
        </View>
      </TouchableOpacity>
    );
  };

  render = () => {
    return (
      <SafeAreaView style={styles.body}>
        <View style={styles.filtersContainer}>
          <FilterItem
            label={'below 1000'}
            selected={this.state.selectedFilter === 0}
            onPress={() => this.onFilterPressed(0)}
          />
          <FilterItem
            label={'1000-10000'}
            selected={this.state.selectedFilter === 1}
            onPress={() => this.onFilterPressed(1)}
          />
          <FilterItem
            label={'10000 and above'}
            selected={this.state.selectedFilter === 2}
            onPress={() => this.onFilterPressed(2)}
          />
        </View>
        {!this.state.loading && this.state.expenses.length > 1 && (
          <FlatList
            data={this.state.expenses}
            renderItem={this.renderItem}
            keyExtractor={item => item.id}
          />
        )}
        {this.state.loading && <Text style={styles.message}>Loading...</Text>}
        {!this.state.loading && this.state.expenses.length === 0 && (
          <Text style={styles.message}>No expenses found</Text>
        )}
        {this.state.error != null && (
          <Text style={styles.message}>{this.state.error}</Text>
        )}
      </SafeAreaView>
    );
  };
}

const styles = StyleSheet.create({
  body: {
    backgroundColor: Colors.white,
    position: 'absolute',
    top: 0,
    bottom: 0,
    left: 0,
    right: 0,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionContainerCentered: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  message: {
    marginTop: 8,
    fontSize: 12,
    fontWeight: '300',
    color: Colors.dark,
    textAlign: 'center',
  },
  highlight: {
    fontWeight: '700',
  },
  footer: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 4,
    paddingRight: 12,
    textAlign: 'right',
  },
  filtersContainer: {
    padding: 8,
    marginBottom: 8,
    backgroundColor: Colors.lighter,
    flexDirection: 'row',
  },
  itemContainer: {
    padding: 8,
    marginHorizontal: 8,
    marginBottom: 8,
    backgroundColor: Colors.light,
  },
  itemName: {
    color: Colors.black,
    fontSize: 14,
    fontWeight: '600',
  },
  itemEmail: {
    color: Colors.dark,
    fontSize: 10,
    fontWeight: 'normal',
  },
  itemPrice: {
    color: Colors.black,
    fontSize: 14,
    fontWeight: '600',
    position: 'absolute',
    right: 8,
    top: 8,
  },
  itemDate: {
    color: Colors.dark,
    fontSize: 10,
    fontWeight: '600',
    position: 'absolute',
    right: 8,
    bottom: 8,
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
