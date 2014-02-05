'''
Created on Oct 25, 2013

@author: Chunqiu Zeng
'''

import numpy as np
import scipy
import matplotlib.pyplot as plt

class Display(object):
    '''
    This class is used to display the sequence 
    of events in random order
    '''


    def __init__(self):
        '''
        Constructor
        '''
        
    def histgram(self,data):
        count, bins, ignored = plt.hist(data, bins=14, normed=True)
        plt.show()
        
    def scatter2 (self,data,data1):
        fig = plt.figure()
        ax = fig.add_subplot(111)
        x =data
        sum =0.0
        for i in range(len(x)):
            x[i] = x[i] +sum
            sum = x[i]
        y=[1 for i in range(len(x))]
        
        print x 
        
        x1=data1
        sum =0.0
        for i in range(len(x1)):
            x1[i] = x1[i] +sum
            sum = x1[i]
        y1=[2 for i in range(len(x1))]
        
        ax.scatter(x, y,color='red',marker='^')
        ax.scatter(x1,y1,color='blue',marker='.')
        ax.set_xlabel(r'Time')
        ax.set_ylabel(r'Event')
        ax.set_title('Event Sequence')
        ax.grid(True)
        plt.show()
        
    def scatter(self,data):
        fig = plt.figure()
        ax = fig.add_subplot(111)
        colors=['red','blue','green']
        markers=['^','.','*']
        for index in range(len(data)):
            x=data[index]
            y=[index+1 for e in x]
            ax.scatter(x,y,color=colors[index%3],marker=markers[index%3])
            
        ax.set_xlabel(r'Time')
        ax.set_ylabel(r'Event')
        ax.set_title('Event Sequence')
#         ticks = (-0.06, 0.061, 0.02)
#         ax.xticks(ticks)
#         ax.yticks(ticks)
#         ax.grid(True)
        plt.show()
        
    def scatterPlot(self,data):
        fig = plt.figure()
        ax = fig.add_subplot(111)
        colors=['red','blue','green']
        markers=['.','.','*']
        x=[e[0] for e in data]
        y=[e[1] for e in data]
        ax.scatter(x,y,color=colors[0],marker=markers[0])
        ax.set_xlabel(r'lag')
        ax.set_ylabel(r'difference')
        ax.set_title('Fb|a - Fb')
        plt.show()
        
    def density(self,data):
        fig = plt.figure()
        ax = fig.add_subplot(111)
        colors=['red','blue','green']
        markers=['.','.','.']
        for index in range(len(data)):
            x=data[index]
            l=len(x)
            y=[(e+1)*1.0/l for e in range(len(x))]
            ax.plot(x,y,color=colors[index%3],marker=markers[index%3])
            
        ax.set_xlabel(r'Time')
        ax.set_ylabel(r'proportion of the appearing event points to all the event points ')
        ax.set_title('Event Sequence')
#         ticks = (-0.06, 0.061, 0.02)
#         ax.xticks(ticks)
#         ax.yticks(ticks)
        ax.grid(True)
        plt.show()
        
    def distribute(self,data1,data2,data3):
        
        fig = plt.figure()
        ax = fig.add_subplot(111)
        colors=['red','blue','green']
        markers=['.','.','.']
        ax.plot(data1[0],data1[1],color=colors[1],marker=markers[1])
        ax.plot(data2[0],data2[1],color=colors[0],marker=markers[0])
        if data3 != None:
            ax.plot(data3[0],data3[1],color=colors[2],marker=markers[2])
                   
        ax.set_xlabel(r'Time')
        ax.set_ylabel(r'probability')
        ax.set_title('lag interval')
#         ticks = (-0.06, 0.061, 0.02)
#         ax.xticks(ticks)
#         ax.yticks(ticks)
        ax.grid(True)
        plt.show()
        
        