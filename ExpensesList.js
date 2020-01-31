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
  ScrollView,
  View,
  Text,
  StatusBar,
  FlatList,
} from 'react-native';

import {Colors, Header} from 'react-native/Libraries/NewAppScreen';

const ExpensesList: () => React$Node = props => {
  return (
    <>
      <StatusBar barStyle="dark-content" />
      <SafeAreaView>
        <View
          contentInsetAdjustmentBehavior="automatic"
          style={styles.scrollView}>
          <View style={styles.body}>
            <Text style={styles.sectionTitle}>Expenses List</Text>
            <View style={styles.sectionContainerCentered}>
              {!props.loading && (
                <Text style={styles.sectionDescription}>Loaded</Text>
              )}
              {!props.loading && (
                <Text style={styles.sectionDescription}>
                  {props.expenses.length}
                </Text>
              )}

              {!props.loading && (
                <FlatList
                  data={props.expenses}
                  renderItem={({item, index}) => {
                    return (
                      <Text style={styles.item}>
                        {index}.{item.id}
                      </Text>
                    );
                  }}
                  keyExtractor={item => item.id}
                />
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
  scrollView: {
    backgroundColor: Colors.lighter,
  },
  engine: {
    position: 'absolute',
    right: 0,
  },
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
  item: {
    color: Colors.dark,
    fontSize: 12,
    fontWeight: '600',
    padding: 8,
  },
});

export default ExpensesList;
