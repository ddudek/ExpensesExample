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
  StatusBar,
  FlatList,
  TouchableOpacity,
} from 'react-native';
import NativeCallbacksBridge from './NativeCallbacksBridge';
import {format, parseISO} from 'date-fns';
import {DeviceEventEmitter} from 'react-native';
import {Colors} from 'react-native/Libraries/NewAppScreen';

interface Props {
  expenses: object;
  loading: Boolean;
  error: String | null;
}

interface State {
  expenses: object;
  loading: Boolean;
  error: String | null;
}

const ExpensesList: () => React$Node = props => {
  const onItemPressed = obj => {
    NativeCallbacksBridge.onExpenseItemClicked(obj.id);
  };

  const renderItem = ({item, index}) => {
    return (
      <TouchableOpacity onPress={() => onItemPressed(item)}>
        <View style={styles.itemContainer}>
          <Text style={styles.itemName}>
            {item.user.first} {item.user.last}
          </Text>
          <Text style={styles.itemEmail}>{item.user.email}</Text>
          <Text style={styles.itemPrice}>
            {item.amount.value} {item.amount.currency}
          </Text>
          <Text style={styles.itemDate}>
            {format(parseISO(item.date), 'MMM do, yyyy H:mma')}
          </Text>
        </View>
      </TouchableOpacity>
    );
  };

  return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView>
        <View
          contentInsetAdjustmentBehavior="automatic"
          style={styles.scrollView}>
          <View style={styles.body}>
            {!props.loading && (
              <FlatList
                data={props.expenses}
                renderItem={renderItem}
                keyExtractor={item => item.id}
              />
            )}
            <View style={styles.sectionContainerCentered}>
              {!props.loading && (
                <Text style={styles.sectionDescription}>
                  {props.expenses.length}
                </Text>
              )}

              {props.loading && (
                <Text style={styles.sectionDescription}>Loading...</Text>
              )}
              {props.error != null && (
                <Text style={styles.sectionDescription}>
                  Error: {props.error}
                </Text>
              )}
            </View>
          </View>
        </View>
      </SafeAreaView>
    </>
  );
};

const styles = StyleSheet.create({
  body: {
    backgroundColor: Colors.white,
  },
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionContainerCentered: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: '600',
    color: Colors.black,
  },
  sectionDescription: {
    marginTop: 8,
    fontSize: 18,
    fontWeight: '400',
    color: Colors.dark,
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
});

export default ExpensesList;
