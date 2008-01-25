package org.apache.myfaces.tobago.layout;

/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * User: lofwyr
 * Date: 24.01.2008 16:31:58
 */
public class Grid {

  private static final Log LOG = LogFactory.getLog(Grid.class);

  private GridArray grid;

  private int columnCursor;
  private int rowCursor;

  private List<Integer> errorIndexes;

  public Grid(int columnCount, int rowCount) {

    grid = new GridArray(columnCount, rowCount);
  }

  public void add(ComponentCell cell, int columnSpan, int rowSpan) {

    assert columnSpan > 0;
    assert rowSpan > 0;

    boolean error = false;

    if (columnSpan + columnCursor > grid.getColumnCount()) {
      LOG.warn("The columnSpan is to large for the actual position in the grid. Will be fixed. " +
          "columnSpan=" + columnSpan + " columnCursor=" + columnCursor + " columnCount=" + grid.getColumnCount() + " ");
      columnSpan = grid.getColumnCount() - columnCursor;
      error = true;
    }

    for (int i = 1; i < columnSpan; i++) {
      if (grid.get(i + columnCursor, rowCursor) != null) {
        LOG.warn("The columnSpan is to large for the actual position in the grid. Will be fixed. " +
            "columnSpan=" + columnSpan + " columnCursor=" + columnCursor + " columnCount=" + grid.getColumnCount() + " ");
        columnSpan = i - 1;
        error = true;
      }
    }

    for (int j = 0; j < rowSpan; j++) {
      for (int i = 0; i < columnSpan; i++) {
        Cell actualCell;
        if (i == 0 && j == 0) {
          actualCell = cell;
        } else {
          actualCell = new SpanCell(cell);
        }
        assert grid.get(i + columnCursor, j + rowCursor) == null : "Position in the cell must be free.";
        grid.set(i + columnCursor, j + rowCursor, actualCell);
        if (error) {
          addError(i + columnCursor, j + rowCursor);
        }
      }
    }

    findNextFreeCell();
  }

  public Cell get(int column, int row) {
    return grid.get(column, row);
  }

  private void findNextFreeCell() {
    for (; rowCursor < grid.getRowCount(); rowCursor++) {
      for (; columnCursor < grid.getColumnCount(); columnCursor++) {
        if (grid.get(columnCursor, rowCursor) == null) {
          return;
        }
      }
      columnCursor = 0;
    }
  }

  public int getColumnCount() {
    return grid.getColumnCount();
  }

  public int getRowCount() {
    return grid.getRowCount();
  }

  public void addError(int i, int j) {
    if (errorIndexes == null) {
      errorIndexes = new ArrayList<Integer>();
    }
    errorIndexes.add(j * grid.getColumnCount() + i);
  }

  public boolean hasError(int i, int j) {
    if (errorIndexes == null) {
      return false;
    }
    return errorIndexes.contains(j * grid.getColumnCount() + i);
  }

  @Override
  public String toString() {

    StringBuilder builder = new StringBuilder();

    // top of grid
    for (int i = 0; i < grid.getColumnCount(); i++) {
      if (i == 0) {
        if (grid.get(i, 0) != null) {
          builder.append("┏");
        } else {
          builder.append("┌");
        }
      } else {
        Cell c = grid.get(i - 1, 0);
        Cell d = grid.get(i, 0);
        if (c == null && d == null) {
          builder.append("┬");
        } else {
          if (connected(c, d)) {
            builder.append("┯");
          } else {
            if (c == null) {
              builder.append("┲");
            } else if (d == null) {
              builder.append("┱");
            } else {
              builder.append("┳");
            }
          }
        }
      }
      if (grid.get(i, 0) != null) {
        builder.append("━");
      } else {
        builder.append("─");
      }

    }
    if (grid.get(grid.getColumnCount() - 1, 0) != null) {
      builder.append("┓");
    } else {
      builder.append("┐");
    }
    builder.append("\n");

    for (int j = 0; j < grid.getRowCount(); j++) {

      // between the cells
      if (j != 0) {
        for (int i = 0; i < grid.getColumnCount(); i++) {
          if (i == 0) {
            Cell b = grid.get(0, j - 1);
            Cell d = grid.get(0, j);
            if (b == null && d == null) {
              builder.append("├");
            } else {
              if (connected(b, d)) {
                builder.append("┠");
              } else {
                if (b == null) {
                  builder.append("┢");
                } else if (d == null) {
                  builder.append("┡");
                } else {
                  builder.append("┣");
                }
              }
            }
          } else {
            Cell a = grid.get(i - 1, j - 1);
            Cell b = grid.get(i, j - 1);
            Cell c = grid.get(i - 1, j);
            Cell d = grid.get(i, j);
//            a│b
//            ─┼─
//            c│d
            if (connected(a, b)) {
              if (connected(c, d)) {
                if (connected(a, c)) {
                  builder.append("┼");
                } else {
                  builder.append("┿");
                }
              } else {
                builder.append("╈");
              }
            } else {
              if (connected(c, d)) {
                if (connected(a, c)) {
                  builder.append("╄");
                } else if (connected(b, d)) {
                  builder.append("╃");
                } else {
                  builder.append("╇");
                }
              } else {
                if (connected(a, c)) {
                  if (connected(b, d)) {
                    builder.append("╂");
                  } else {
                    builder.append("╊");
                  }
                } else {
                  if (connected(b, d)) {
                    builder.append("╉");
                  } else {
                    builder.append("╋");
                  }
                }
              }
            }
          }
          Cell a = grid.get(i, j - 1);
          Cell c = grid.get(i, j);
          if (connected(a, c)) {
            builder.append("─");
          } else {
            builder.append("━");
          }
        }
        Cell a = grid.get(grid.getColumnCount() - 1, j - 1);
        Cell c = grid.get(grid.getColumnCount() - 1, j);
        if (a == null && c == null) {
          builder.append("┤");
        } else {
          if (connected(a, c)) {
            builder.append("┨");
          } else {
            if (a == null) {
              builder.append("┪");
            } else if (c == null) {
              builder.append("┩");
            } else {
              builder.append("┫");
            }
          }
        }
        builder.append("\n");
      }

      // cell
      for (int i = 0; i < grid.getColumnCount(); i++) {
        if (i == 0) {
          if (grid.get(i, j) != null) {
            builder.append("┃");
          } else {
            builder.append("│");
          }
        } else {
          Cell c = grid.get(i - 1, j);
          Cell d = grid.get(i, j);
          if (connected(c, d)) {
            builder.append("│");
          } else {
            builder.append("┃");
          }
        }
        if (hasError(i, j)) {
          builder.append("✖"); //↯
        } else {
          if (grid.get(i, j) instanceof ComponentCell) {
            builder.append("█");
          } else if (grid.get(i, j) instanceof SpanCell) {
            if (j == 0) {
              builder.append("➞");
            } else {
              Cell a = grid.get(i, j - 1);
              Cell c = grid.get(i, j);
              if (connected(a, c)) {
                builder.append("⬇");
              } else {
                builder.append("➞");
              }
            }
          } else {
            builder.append("◌");
          }
        }
      }
      if (grid.get(grid.getColumnCount() - 1, j) != null) {
        builder.append("┃");
      } else {
        builder.append("│");
      }
      builder.append("\n");
    }

    //last bottom
    for (int i = 0; i < grid.getColumnCount(); i++) {
      if (i == 0) {
        if (grid.get(0, grid.getRowCount() - 1) != null) {
          builder.append("┗");
        } else {
          builder.append("└");
        }
      } else {
        Cell a = grid.get(i - 1, grid.getRowCount() - 1);
        Cell b = grid.get(i, grid.getRowCount() - 1);
        if (a == null && b == null) {
          builder.append("┴");
        } else {
          if (connected(a, b)) {
            builder.append("┷");
          } else {
            if (a == null) {
              builder.append("┺");
            } else if (b == null) {
              builder.append("┹");
            } else {
              builder.append("┻");
            }
          }
        }
      }
      if (grid.get(i, grid.getRowCount() - 1) != null) {
        builder.append("━");
      } else {
        builder.append("─");
      }
    }
    if (grid.get(grid.getColumnCount() - 1, grid.getRowCount() - 1) != null) {
      builder.append("┛");
    } else {
      builder.append("┘");
    }
    builder.append("\n");

    return builder.toString();
  }

  private boolean connected(Cell a, Cell b) {
    if (a == null && b == null) {
      return true;
    }
    if (a == null || b == null) {
      return false;
    }
    return a.getMasterCell().equals(b.getMasterCell());
  }
}
