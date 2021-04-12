import numpy as np
from py_expression_eval import Parser
import matplotlib.pyplot as plt
import sys

function = Parser().parse(sys.argv[1])
file = open(sys.argv[2], "r")
contourLevels = []
ax = plt.subplot()

print("started")
def evaluate(x, y):
    return function.evaluate({"x": x, "y": y})


for line in file:
    x, y, dx, dy = [float(x) for x in line.split()]
    contourLevels.append(function.evaluate({"x": x, "y": y}))
    ax.arrow(x, y,
             dx, dy,
             length_includes_head=True,
             head_width=0.02, head_length=0.01)
contourLevels.sort()
xlist = np.linspace(-int(sys.argv[3]), int(sys.argv[3]), 100)
ylist = np.linspace(-int(sys.argv[3]), int(sys.argv[3]), 100)
X, Y = np.meshgrid(xlist, ylist)
Z = np.fromiter(map(evaluate, X.ravel(), Y.ravel()), X.dtype).reshape(X.shape)
ax.contour(X, Y, Z, contourLevels)
ax.set_xlabel("y")
ax.set_ylabel("x")
plt.show()
