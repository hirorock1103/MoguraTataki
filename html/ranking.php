<?php

$dsn = 'mysql:dbname=mdiz1103_apppractice;host=mysql1203.xserver.jp;port=3306';
$user = 'mdiz1103_hiro';
$password = '11032189';

$pdo = null;

try {
    $pdo = new PDO($dsn, $user, $password);
} catch (PDOException $e) {
    echo 'Error:' . $e->getMessage();
    die();
}

$reqeustMethod = mb_strtolower($_SERVER['REQUEST_METHOD']);
if ($reqeustMethod === 'get') {
    header('Content-Type: application/json;charset=UTF-8');
    header('Access-Control-Allow-Origin: *');

    $sql .= 'select ';
    $sql .= '  rank, ';
    $sql .= '  level, ';
    $sql .= '  hit_count, ';
    $sql .= '  miss_count, ';
    $sql .= '  player_name, ';
    $sql .= '  createdate, ';
    $sql .= '  updatedate ';
    $sql .= 'from ';
    $sql .= '  mogura_tataki ';
    $sql .= 'order by ';
    $sql .= '  hit_count desc ';
    $sql .= 'limit ';
    $sql .= '  10; ';

    $stmt = $pdo->prepare($sql);
    if (!$stmt->execute()) {
        $info = $stmt->errorInfo();
        exit($info[2]);
    }

    $json = array(
        'ranking' => array_map(function ($it) {
            $it['rank'] = (int) $it['rank'];
            $it['level'] = (int) $it['level'];
            $it['hit_count'] = (int) $it['hit_count'];
            $it['miss_count'] = (int) $it['miss_count'];

            return $it;
        }, $stmt->fetchAll()),
    );

    echo json_encode($json);
} else {
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
    $level = $_POST['level'];
    $hitCount = $_POST['hit_count'];
    $missCount = $_POST['miss_count'];
    $playerName = $_POST['player_name'];

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
