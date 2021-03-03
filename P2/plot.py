import pandas as pd
import matplotlib.pyplot as plt
import numpy as np
import statsmodels.api as sm
from statsmodels.formula.api import ols


def plot(fn: str):

    # read csv
    df = pd.read_csv(fn)

    # create pyplot figure
    fig = plt.figure(figsize=(12, 8))

    # run model
    model = ols('r ~ n', data=df).fit()

    # generate plots from model
    fig = sm.graphics.plot_regress_exog(model, 'n', fig=fig)
    fig.savefig("plots")

    # write stats to file
    with open("stats.txt", "w") as file:
        file.write(str(model.summary()))


if __name__ == "__main__":
    # run the plot cmd
    plot("runtimes.csv")
