<?php

$reqeustMethod = mb_strtolower($_SERVER['REQUEST_METHOD']);
if ($reqeustMethod === 'get') {
    header('Content-Type: application/json;charset=UTF-8');
    header('Access-Control-Allow-Origin: *');

    $json = array(
        'ranking' => array(
            array(
                'ranking_id' => 10,
                'rank' => 20,
                'level' => '3',
                'hit_count' => 'aaa',
                'miss_count' => 'bbb',
                'player_name' => 'cc',
                'createdate' => '2018-06-15 12:00:00',
                'updatedate' => '2018-06-15 12:00:00',
            ),
            array(
                'ranking_id' => '',
                'rank' => '',
                'level' => '',
                'hit_count' => '',
                'miss_count' => '',
                'player_name' => '',
                'createdate' => '',
                'updatedate' => '',
            ),
        ),
    );

    echo json_encode($json);
} else {
    $dsn = 'mysql:dbname=mdiz1103_apppractice;host=mysql1203.xserver.jp;port=3306';
    $user = 'mdiz1103_hiro';
    $password = '11032189';

    try {
        $pdo = new PDO($dsn, $user, $password);
    } catch (PDOException $e) {
        echo 'Error:' . $e->getMessage();
        die();
    }

    $sql = '';
    $sql .= 'insert into mogura_tataki( ';
    $sql .= '  rank, ';
    $sql .= '  level, ';
    $sql .= '  hit_count, ';
    $sql .= '  miss_count, ';
    $sql .= '  player_name, ';
    $sql .= '  createdate, ';
    $sql .= '  updatedate ';
    $sql .= ') ';
    $sql .= 'values ';
    $sql .= '  ( ';
    $sql .= '    :rank, ';
    $sql .= '    :level, ';
    $sql .= '    :hit_count, ';
    $sql .= '    :miss_count, ';
    $sql .= '    :player_name, ';
    $sql .= '    now(), ';
    $sql .= '    now() ';
    $sql .= '  ); ';

    $rank = $_POST['rank'];
    $level = $_POST['rank'];
    $hitCount = $_POST['hit_count'];
    $missCount = $_POST['miss_count'];
    $playerName= $_POST['player_name'];

    $stmt = $pdo->prepare($sql);
    $stmt->bindValue(':rank', $rank, PDO::PARAM_INT);
    $stmt->bindValue(':level', $level, PDO::PARAM_INT);
    $stmt->bindValue(':hit_count', $hitCount, PDO::PARAM_INT);
    $stmt->bindValue(':miss_count', $missCount, PDO::PARAM_INT);
    $stmt->bindValue(':player_name', $playerName, PDO::PARAM_STR);
    if (!$stmt->execute()) {
        $info = $stmt->errorInfo();
        exit($info[2]);
    }

    echo 'OK';
}
