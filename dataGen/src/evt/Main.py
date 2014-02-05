'''
Created on Oct 25, 2013

@author: grand
'''
from evt.display.Display import Display
from evt.simulation.Simulation import Simulation

if __name__ == '__main__':
    sim = Simulation()
#     data = sim.generateEventSeq(5, 100)
    data = sim.generateDependenceSeq(10, 200)
    display = Display()
    display.scatter(data)
    display.density(data)
    data1 = sim.estimateDistr(data[1])
    print "data1", data1
    data2 = sim.estimateConditionalDistr(data)
    print "data2", data2
#     data3 = sim.estimateConditionalDistr([data[2],data[1]])
    data3=None
    display.distribute(data1, data2,data3)
    data=sim.diffDistr(data2, data1)
    print "diff data",data
    display.scatterPlot(data)
    data = sim.derivative(data)
    print "derivative",data
    display.scatterPlot(data)
    sData= sim.sortDescending(data)
    print "sData",sData
    delta = 0.1
    K=10
    topK=[]
    for i in range(len(sData)):
        if(i>= len(sData)):
            break
        topK.append((sData[i][0]-delta,sData[i][0]+delta,sim.findConfidence(sData[i][0], delta, data2)))
    topK=sim.sortDescending(topK)
    for i in range(K):
        print "interval(",str(topK[i][0]),",",str(topK[i][1]),") conf=",str(topK[i][2])
#     print data
    
